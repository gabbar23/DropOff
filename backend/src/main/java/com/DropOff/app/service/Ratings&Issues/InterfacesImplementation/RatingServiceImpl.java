package com.DropOff.app.service;

import com.DropOff.app.model.Rating;
import com.DropOff.app.model.Authentication.UserProfile;
import com.DropOff.app.model.Courier.DropOffRoutes;
import com.DropOff.app.repository.RatingRepository;
import com.DropOff.app.repository.Authentication.UserRepository;
import com.DropOff.app.repository.Courier.DriverRouteRepository;
import com.DropOff.app.service.Authentication.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    RatingRepository ratingRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    DriverRouteRepository driverRouteRepo;
    @Autowired
    UserProfileService userService;

    /**
     * Store rating
     *
     * 
     * @param driver_route_id driver route id
     * @param star star
     * @param review review
     * @return String rating is posted
     */
    @Override
    public String storeRating(Long driver_route_id, float star, String review){
        try {
            Rating rating = new Rating();
            DropOffRoutes driverRoute = driverRouteRepo.getDriverRouteById(driver_route_id);

            UserProfile sender = userService.getLoggedInUser();

            if(driverRoute==null || sender==null)
                return "Not able to post rating";

            rating.setUser(sender);
            rating.setDriverRoute(driverRoute);
            rating.setStar(star);
            rating.setReview(review);

            ratingRepo.save(rating);

        }catch (Exception e){
            return e.getMessage();
        }
        return "Rating is posted";
    }

    /**
     * Get rating
     *
     * 
     * @param rating_id rating id
     * @return List<Rating>
     */
    @Override
    public String deleteRating(Integer rating_id){
        try {
            Rating rating = ratingRepo.getRatingById(rating_id);

            ratingRepo.delete(rating);

        }catch (Exception e){
            return e.getMessage();
        }
        return "Rating deleted";
    }

    /**
     * Get deliverer rating
     *
     * 
     * @return List<Rating>
     */
    @Override
    public List<Rating> getDelivererRating(){
        try {

            UserProfile deliverer = userService.getLoggedInUser();

            List<Rating> ratings = ratingRepo.getAllByDriverRoute_DriverId(String.valueOf(deliverer.getUser_id()));

            return ratings;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get sender posted rating
     *
     * 
     * @return List<Rating>
     */
    @Override
    public List<Rating> getSenderPostedRating(){
        try {
            UserProfile sender = userService.getLoggedInUser();

            List<Rating> ratings = ratingRepo.getAllByUser_Id(sender.getUser_id());

            return ratings;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get deliverer rating with id
     *
     * 
     * @param id deliverer id
     * @return List<Rating>
     */
    @Override
    public List<Rating> getDelivererRatingWithID(Integer id){
        try {

            UserProfile deliverer = userRepo.getUserById(id);

            List<Rating> ratings = ratingRepo.getAllByDriverRoute_DriverId(String.valueOf(deliverer.getUser_id()));

            return ratings;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
