package groom.him.domain.product.service;

import groom.him.domain.product.exception.BrandErrorCode;
import groom.him.domain.product.exception.BrandException;
import groom.him.domain.product.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public void checkBrandExist(String brandName) {
        if (!brandRepository.existsByEnBrandName(brandName)) {
            throw new BrandException(BrandErrorCode.BRAND_NOT_EXIST);
        }
    }
}