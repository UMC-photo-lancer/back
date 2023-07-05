package shop.photolancer.photolancer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.photolancer.photolancer.converter.TestConverter;
import shop.photolancer.photolancer.domain.Test;
import shop.photolancer.photolancer.repository.TestRepository;
import shop.photolancer.photolancer.service.TestService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestConverter testConverter;
    private final TestRepository testRepository;

    @Transactional
    @Override
    public Test create(String name){
        Test test = testConverter.toTest(name);
        return testRepository.save(test);
    }
}
