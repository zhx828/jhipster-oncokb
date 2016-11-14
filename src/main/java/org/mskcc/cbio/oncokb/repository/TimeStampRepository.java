package org.mskcc.cbio.oncokb.repository;

import org.mskcc.cbio.oncokb.domain.TimeStamp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TimeStamp entity.
 */
@SuppressWarnings("unused")
public interface TimeStampRepository extends JpaRepository<TimeStamp,Long> {

    @Query("select timeStamp from TimeStamp timeStamp where timeStamp.user.login = ?#{principal.username}")
    List<TimeStamp> findByUserIsCurrentUser();

}
