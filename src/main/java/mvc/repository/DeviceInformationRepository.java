package mvc.repository;

import mvc.model.entity.DeviceInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceInformationRepository extends JpaRepository<DeviceInformation,Long> {

    DeviceInformation save(DeviceInformation device);
}
