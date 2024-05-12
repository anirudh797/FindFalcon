package com.anirudh.findfalcone.domain.util

//import com.anirudh.shaadi.data.localDb.EntityProfileInfo


//fun List<EntityProfileInfo>.toProfilesInfoList(): List<ProfileInfo> {
//    return this.map {
//        ProfileInfo(
//            gender = it.gender,
//            location = Location(state = it.state, country = it.country),
//            name = Name(first = it.firstName, last = it.lastName),
//            picture = Picture(large = it.imageUrl, medium = it.imageUrl),
//            profileStatus = it.status,
//            login = Login(username = it.userName),
//            email = it.email
//        )
//    }
//}
//
//fun List<ProfileInfo>.toEntityProfileInfoList(): List<EntityProfileInfo> {
//    return this.map {
//        EntityProfileInfo(
//            gender = it.gender ?: "",
//            state = it.location?.state ?: "",
//            country = it.location?.country ?: "",
//            imageUrl = it.picture?.large ?: "",
//            firstName = it.name?.first ?: "",
//            lastName = it.name?.last ?: "",
//            userName = it.login?.username ?: "",
//            status = it.profileStatus ?: ProfileStatus.NONE ,
//            email = it.email
//        )
//    }
//}
//
//fun ProfileInfo.toEntityProfileInfo(): EntityProfileInfo {
//    return EntityProfileInfo(
//        gender = gender ?: "",
//        state = location?.state ?: "",
//        country = location?.country ?: "",
//        imageUrl = picture?.large ?: "",
//        firstName = name?.first ?: "",
//        lastName = name?.last ?: "",
//        userName = login?.username ?: "",
//        status = profileStatus ?: ProfileStatus.NONE,
//        email = email
//    )
//}
