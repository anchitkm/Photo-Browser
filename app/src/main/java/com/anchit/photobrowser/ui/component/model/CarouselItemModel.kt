package com.anchit.photobrowser.ui.component.model

import java.io.Serializable

/**
 * This carousel model will be updated on response from the respective API.
 * This is defining the protocol that needs to be created for Carousel.
 * @param carouselUrl -  This is the url for loading the images that needs to be loaded to carousel item.
 * @param photoId - This is the photo id of the images in the carousel Pager, to fetch photo details.
 */
data class CarouselItemModel(var carouselUrl:String,var photoId:String): Serializable