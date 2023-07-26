package com.goskate.goskate.data.map

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.goskate.goskate.data.models.Spot
import com.goskate.goskate.domain.models.Spot as DomainSpot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val database: FirebaseDatabase,
) : MapsRepository {

    override suspend fun getAllSpots(): Flow<Result<List<DomainSpot>>> =
        callbackFlow {
            database.getReference("spots").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val spots = mutableListOf<DomainSpot>()

                    if (dataSnapshot.exists()) {
                        dataSnapshot.children.forEach { data ->
                            val spot = data.getValue(Spot::class.java)
                            spots.add(spot?.toDomainSpot() ?: DomainSpot())
                        }
                        trySend(Result.success(spots))
                        close()
                    } else {
                        trySend(Result.failure(Exception("Data is null")))
                        close()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    trySend(Result.failure(Exception(databaseError.message)))
                }
            })
            awaitClose()
        }.flowOn(Dispatchers.IO)
}
