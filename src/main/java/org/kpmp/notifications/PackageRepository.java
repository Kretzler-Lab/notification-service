package org.kpmp.notifications;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PackageRepository extends MongoRepository<Package, String> {

	public Package findByPackageId(String packageId);

}
