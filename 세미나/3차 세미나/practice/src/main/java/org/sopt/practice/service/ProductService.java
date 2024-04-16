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
    public Product addProduct(ProductAddDto productAddDto) {
        Member member = memberRepository.findById(productAddDto.memberId()) //Jpa이용해서 id 찾기
                .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 사용자가 존재하지 않습니다."));
        //만약에 입력한 memberId가 없으면 해당 메시지 출력
        Product product = Product.builder() //상품 추가시 제목 / 가격 / 설명 / 주소 / 사용자(누군지)
                .title(productAddDto.title())
                .price(productAddDto.price())
                .description(productAddDto.description())
                .address(productAddDto.address())
                .member(member)
                .build();
        return productRepository.save(product); //저장해준다

    }


}
