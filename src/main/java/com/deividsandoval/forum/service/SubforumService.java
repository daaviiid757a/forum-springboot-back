package com.deividsandoval.forum.service;

import com.deividsandoval.forum.dto.SubforumDTO;
import com.deividsandoval.forum.exceptions.ForumException;
import com.deividsandoval.forum.mapper.SubforumMapper;
import com.deividsandoval.forum.models.Subforum;
import com.deividsandoval.forum.repository.SubforumRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubforumService {
    private final SubforumRepository subforumRepository;
    private final SubforumMapper subforumMapper;
    private final AuthService authService;

    @Transactional
    public SubforumDTO save(SubforumDTO subforumDTO) {
        Subforum save = subforumRepository.save(subforumMapper.toMap(subforumDTO, authService.getCurrentUser()));

        subforumDTO.setId(save.getId());

        return subforumDTO;
    }

    @Transactional
    public List<SubforumDTO> getAll() {
        return subforumRepository.findAll().stream().map(subforumMapper::toDto).collect(Collectors.toList());
    }

    public SubforumDTO getOne(int id) {
        Subforum subforum = subforumRepository.findById(id).orElseThrow(() -> new ForumException("No subforum found"));

        return subforumMapper.toDto(subforum);
    }
}
