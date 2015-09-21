package name.abhijitsarkar.javaee.userpref.repository;

import java.util.Optional;

import name.abhijitsarkar.javaee.userpref.domain.UserPreference;

public interface UserPreferenceRepository {
    Optional<UserPreference> findOne(UserPreference userPreference);

    Optional<UserPreference> save(UserPreference userPreference);

    Optional<UserPreference> delete(UserPreference userPreference);
}
