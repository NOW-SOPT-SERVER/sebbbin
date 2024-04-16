package org.sopt.practice.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.sopt.practice.domain.Member;
import org.sopt.practice.domain.Product;
import org.sopt.practice.dto.product.ProductAddDto;
import org.sopt.practice.repository.MemberRepository;
import org.sopt.practice.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Product addProduct(ProductAddDto productAddDto){
        Member member = memberRepository.findById(productAddDto.memberId())
                .orElseThrow(()-> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));

        Product product = Product.builder()
                        .title(productAddDto.title())
                .price(productAddDto.price())
                .description(productAddDto.description())
                .address(productAddDto.address())
                .member(member)
                .build();
        return productRepository.save(product);

    }


}
