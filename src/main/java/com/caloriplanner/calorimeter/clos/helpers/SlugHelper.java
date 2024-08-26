package com.caloriplanner.calorimeter.clos.helpers;

import com.caloriplanner.calorimeter.clos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Locale;

@Component
public class SlugHelper {

    @Autowired
    private UserRepository userRepository;

    public String generateSlug(String username){
        String normalised = Normalizer.normalize(username, Normalizer.Form.NFD);
        return normalised.replaceAll("[^\\w]", "-").toLowerCase(Locale.ENGLISH);
    }

    public String ensureSlugUnique(String baseSlug){
        int suffix = 1;
        String slug = baseSlug;
        while (checkSlugExists(slug)){
            slug = baseSlug + '-' + suffix;
            suffix++;
        }

        return slug;
    }

    public boolean checkSlugExists(String slug){
        return userRepository.existsBySlug(slug);
    }
}
