package com.tomgilbert.core.data.model

import com.tomgilbert.core.model.FolderItem
import com.tomgilbert.core.model.UserData
import com.tomgilbert.core.network.model.NetworkUserData

fun NetworkUserData.asExternalModel(): UserData {
    return UserData(firstName, lastName, rootItem.asExternalModel() as FolderItem)
}