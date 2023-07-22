package com.goskate.goskate.data.map

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.goskate.goskate.domain.models.Spot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
) : MapsRepository {

    override suspend fun getAllSpots(): Flow<Result<List<Spot>>> =
        callbackFlow {
            database.getReference("spots").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val spots = mutableListOf<Spot>()

                    if (dataSnapshot.exists()) {
                        dataSnapshot.children.forEach { data->
                        }
                        trySend(Result.success(listOf()))
                    } else {
                        trySend(Result.failure(Exception("Data is null")))
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    trySend(Result.failure(Exception(databaseError.message)))
                }
            })
        }
}
