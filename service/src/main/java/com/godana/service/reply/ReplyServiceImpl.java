package com.godana.service.reply;

import com.godana.domain.entity.Reply;
import com.godana.repository.reply.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ReplyServiceImpl implements IReplyService{
    @Autowired
    private ReplyRepository replyRepository;
    @Override
    public List<Reply> findAll() {
        return replyRepository.findAll();
    }

    @Override
    public Optional<Reply> findById(Long id) {
        return replyRepository.findById(id);
    }

    @Override
    public Reply save(Reply reply) {
        return replyRepository.save(reply);
    }

    @Override
    public void delete(Reply reply) {
        replyRepository.delete(reply);
    }

    @Override
    public void deleteById(Long id) {
        replyRepository.deleteById(id);
    }
}
