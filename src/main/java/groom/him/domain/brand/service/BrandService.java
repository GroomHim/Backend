package groom.him.domain.brand.service;

import groom.him.domain.brand.exception.BrandErrorCode;
import groom.him.domain.brand.exception.BrandException;
import groom.him.domain.brand.models.dto.response.BrandResponse;
import groom.him.domain.brand.models.entity.BrandEntity;
import groom.him.domain.brand.repository.BrandRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public void checkBrandExistByEnBrandName(String brandName) {
        if (!brandRepository.existsByEnBrandName(brandName)) {
            throw new BrandException(BrandErrorCode.BRAND_NOT_EXIST);
        }
    }

    public List<BrandResponse> findBrandList() {
        List<BrandEntity> brandEntityList = brandRepository.findAll();
        return brandEntityList.stream().map(BrandResponse::from).toList();
    }
}