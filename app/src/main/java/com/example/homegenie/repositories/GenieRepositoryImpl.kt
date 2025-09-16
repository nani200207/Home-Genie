package com.example.homegenie.repositories

import com.example.homegenie.models_.CartDetailsModel
import com.example.homegenie.models_.CustomerDetailsModel
import com.example.homegenie.models_.CustomerLocationDetailsModel
import com.example.homegenie.utils.ResultState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GenieRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) : GenieRepository {
    override fun insertItemToCart(item: CartDetailsModel.CartItem): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            db.collection("cart").add(item)
                .addOnSuccessListener {
                    trySend(ResultState.Success("Added to cart!"))
                }.addOnFailureListener {
                    trySend(ResultState.Failure(it))
                }
            awaitClose {
                close()
            }

        }

    override fun removeCartItem(key: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        db.collection("cart").document(key)
            .delete()
            .addOnCompleteListener {
                if (it.isSuccessful)
                    trySend(ResultState.Success("Removed from cart!"))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun fetchCartItems(): Flow<ResultState<List<CartDetailsModel>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            val snapshotListener =
                db.collection("cart").whereEqualTo("uid", Firebase.auth.currentUser?.uid)
                    .addSnapshotListener { snapshot, e ->
                        if (e != null) {
                            close(e)
                        }
                        if (snapshot != null) {
                            val items = snapshot.map { data ->
                                CartDetailsModel(
                                    item = CartDetailsModel.CartItem(
                                        uid = data["uid"] as String?,
                                        itemName = data["itemName"] as String?,
                                        itemPrice = data["itemPrice"] as Double?,
                                        itemQty = data["itemQty"] as Long?,
                                    ),
                                    key = data.id
                                )
                            }
                            trySend(ResultState.Success(items))
                        }
                    }
            awaitClose {
                snapshotListener.remove()
            }
        }

    //Customer

    override fun insertC(item: CustomerDetailsModel.CustomerItem): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            db.collection("customers").document(Firebase.auth.currentUser?.uid!!)
                .set(item)
                .addOnSuccessListener {
                    trySend(ResultState.Success("Registered successfully!"))
                }.addOnFailureListener {
                    trySend(ResultState.Failure(it))
                }
            awaitClose {
                close()
            }
        }


    override fun getItemC(): Flow<ResultState<CustomerDetailsModel>> = callbackFlow {
        trySend(ResultState.Loading)
        val snapshotListener =
            db.collection("customers").document(Firebase.auth.currentUser?.uid!!)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        close(e)
                    }
                    if (snapshot != null) {
                        val item = CustomerDetailsModel(
                            CustomerDetailsModel.CustomerItem(
                                cid = snapshot["cid"] as String?,
                                name = snapshot["name"] as String?,
                                gender = snapshot["gender"] as String?,
                                age = snapshot["age"] as Long?,
                                mobileNumber = snapshot["mobileNumber"] as Long?,
                            ),
                            key = snapshot.id
                        )
                        trySend(ResultState.Success(item))
                    }
                }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun checkItemC(): Flow<ResultState<Boolean>> = callbackFlow {
        trySend(ResultState.Loading)
        db.collection("customers").document(Firebase.auth.currentUser?.uid!!).get()
            .addOnSuccessListener {
                val exists = it.exists()
                trySend(ResultState.Success(exists))
            }.addOnFailureListener {
                trySend(ResultState.Success(data = false))
            }

        awaitClose {
            close()
        }
    }


    override fun deleteC(key: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        db.collection("customers")
            .document(key)
            .delete()
            .addOnCompleteListener {
                if (it.isSuccessful)
                    trySend(ResultState.Success("Deleted successfully.."))
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun updateC(item: CustomerDetailsModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            val map = HashMap<String, Any>()
            map["name"] = item.item?.name!!
            map["gender"] = item.item.gender!!
            map["age"] = item.item.age!!
            map["mobileNumber"] = item.item.mobileNumber!!

            db.collection("customers")
                .document(item.key!!)
                .update(map)
                .addOnCompleteListener {
                    if (it.isSuccessful)
                        trySend(ResultState.Success("Updated successfully..."))
                }.addOnFailureListener {
                    trySend(ResultState.Failure(it))
                }
            awaitClose {
                close()
            }
        }

    //Locations

    override fun insertL(item: CustomerLocationDetailsModel.CustomerLocationItem): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            db.collection("customer_locations").document(Firebase.auth.currentUser?.uid!!)
                .set(item)
                .addOnSuccessListener {
                    trySend(ResultState.Success("Location saved"))
                }.addOnFailureListener {
                    trySend(ResultState.Failure(it))
                }
            awaitClose {
                close()
            }
        }

    override fun getLocation(): Flow<ResultState<CustomerLocationDetailsModel>> = callbackFlow {
        trySend(ResultState.Loading)

        val snapshotListener =
            db.collection("customer_locations").document(Firebase.auth.currentUser?.uid!!)
                .addSnapshotListener { snapshot, e ->

                    if (e != null) {
                        close(e)
                    }

                    if (snapshot != null) {
                        val item =
                            CustomerLocationDetailsModel(
                                item = CustomerLocationDetailsModel.CustomerLocationItem(
                                    uid = snapshot["uid"] as String?,
                                    lat = snapshot["lat"] as Double?,
                                    lng = snapshot["lng"] as Double?,
                                    geoHash = snapshot["geoHash"] as String?,
                                    houseNumber = snapshot["houseNumber"] as String?,
                                    locality = snapshot["locality"] as String?
                                ),
                                key = snapshot.id
                            )

                        trySend(ResultState.Success(item))
                    }
                }
        awaitClose {
            snapshotListener.remove()
        }
    }

//    override fun deleteL(key: String): Flow<ResultState<String>> = callbackFlow {
//        trySend(ResultState.Loading)
//        db.collection("customer_locations")
//            .document(key)
//            .delete()
//            .addOnCompleteListener {
//                if (it.isSuccessful)
//                    trySend(ResultState.Success("Deleted!"))
//            }.addOnFailureListener {
//                trySend(ResultState.Failure(it))
//            }
//        awaitClose {
//            close()
//        }
//    }

}


