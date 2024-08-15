package com.example.stock.repository;

import com.example.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// 편의를 위해 동일한 엔티티로 설정, 실무에서는 데이터소스를 분리해서 사용하는 걸 권장
// 같은 데이터소스를 사용하면 커넥션풀이 부족해지는 현상 발생, 다른 서비스의 성능에도 영향이 감
public interface LockRepository extends JpaRepository<Stock, Long> {

    @Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
    void getLock(String key);

    @Query(value = "select release_lock(:key)", nativeQuery = true)
    void releaseLock(String key);

}
