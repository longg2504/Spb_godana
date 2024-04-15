package com.godana.service.rating;

import com.godana.domain.dto.rating.RatingCreReqDTO;
import com.godana.domain.dto.rating.RatingCreResDTO;
import com.godana.domain.dto.rating.RatingDTO;
import com.godana.domain.entity.Place;
import com.godana.domain.entity.Rating;
import com.godana.domain.entity.User;
import com.godana.exception.DataInputException;
import com.godana.repository.rating.RatingRepository;
import com.godana.service.place.IPlaceService;
import com.godana.service.user.IUserService;
import com.godana.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RatingServiceImpl implements IRatingService{
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private IPlaceService iPlaceService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ValidateUtils validateUtils;
    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll();
    }

    @Override
    public Optional<Rating> findById(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public void delete(Rating rating) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<RatingDTO> findAllByPlaceId(Long placeId) {
        return ratingRepository.findAllByPlaceId(placeId);
    }

    @Override
    public RatingCreResDTO create(RatingCreReqDTO ratingCreReqDTO) {
        Optional<Place> placeOptional = iPlaceService.findById(ratingCreReqDTO.getPlaceId());
        if(!placeOptional.isPresent()){
            throw new DataInputException("Địa điểm muốn đánh giá không tồn tại");
        }
        Place place = placeOptional.get();

        Optional<User> userOptional = iUserService.findById(ratingCreReqDTO.getUserId());
        if(!userOptional.isPresent()){
            throw new DataInputException("Tài khoản đánh giá không tồn tại vui lòng xem lại tài khoản");
        }
        User user = userOptional.get();

        Rating rating = ratingCreReqDTO.toRating(user,place);
        rating = ratingRepository.save(rating);

        RatingCreResDTO ratingCreResDTO = rating.toRaingCreResDTO();

        return ratingCreResDTO;
    }
}
