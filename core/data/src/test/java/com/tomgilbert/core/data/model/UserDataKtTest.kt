package com.tomgilbert.core.data.model

import com.tomgilbert.core.network.model.NetworkFileSystemItem
import com.tomgilbert.core.network.model.NetworkUserData
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class UserDataKtTest {

    @Test
    fun network_userdata_can_be_mapped_to_userdata() {
        val now = Instant.now().toString()
        val home = NetworkFileSystemItem(
            id = "1",
            parentId = null,
            name = "Home",
            isDir = true,
            modificationDate = now,
            size = null,
            contentType = null
        )
        val networkUserData = NetworkUserData(
            "Tiny", "Tim", home
        )
        val userData = networkUserData.asExternalModel()
        assertEquals("Tiny", userData.firstName)
        assertEquals("Tim", userData.lastName)
        assertEquals(home.asExternalModel(), userData.home)
    }

    @Test(expected = Exception::class)
    fun network_userdata_only_makes_sense_with_root_item_as_folder() {
        val now = Instant.now().toString()
        val home = NetworkFileSystemItem(
            id = "1",
            parentId = null,
            name = "readme.txt",
            isDir = false,
            modificationDate = now,
            size = 12345,
            contentType = null
        )
        val networkUserData = NetworkUserData(
            "Tiny", "Tim", home
        )
        networkUserData.asExternalModel()
    }
}