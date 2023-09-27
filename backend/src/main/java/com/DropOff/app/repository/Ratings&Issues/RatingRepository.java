package com.DropOff.app.repository;

import com.DropOff.app.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer> {

    /**
     * getRatingById is a method to get rating by id
     *
   
     * @param rating_id rating id
     * @return Rating object
     */
    Rating getRatingById(Integer rating_id);

    /**
     * getAllByDriverRoute_DriverId is a method to get all ratings by driver id
     *
     * 
     * @param driver_id driver id
     * @return List<Rating>
     */
    List<Rating> getAllByDriverRoute_DriverId(String driver_id);

    /**
     * getAllByUser_Id is a method to get all ratings by user id
     *

     * @param sender_id sender id
     * @return List<Rating>
     */
    List<Rating> getAllByUser_Id(Integer sender_id);
}
