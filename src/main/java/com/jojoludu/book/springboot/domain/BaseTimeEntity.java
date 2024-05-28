package com.jojoludu.book.springboot.domain;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * BaseTimeEntity 모든 Entity 상위 클래스가 되어 Entity들의 createdDate, modifiedDate를 자동으로 관리하는 역활
 *
 * 1. @MappedSuperclass
 *      - JPA Entity 클래스들이 BaseTimeEntity을 상속할 경우 필드들도 컬럼으로 인식하도록 해줌
 *
 * 2. @EntityListeners(AuditingEntityListener.class)
 *      - BaseTimeEntity 클래스에 Auditing(자동으로 값을 넣어주는) 기능을 포함 시킴
 * 3. @CreatedDate
 *      -Entity가 생성되어 저장될 때의 시간을 자동으로 저장
 * 4. @LastModifiedDate
 *      - 조회한 Entity가 값을 변경할 때 시간이 자동으로 저장됨
 * **/
@Getter
@MappedSuperclass   //1.
@EntityListeners(AuditingEntityListener.class)  //2.
public abstract class BaseTimeEntity {

    @CreatedDate    //3.
    private LocalDateTime createDate;

    @LastModifiedDate   //4.
    private LocalDateTime modifiedDate;

}
