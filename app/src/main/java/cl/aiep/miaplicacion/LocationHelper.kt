

package cl.aiep.miaplicacion

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LocationHelper(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun saveUserLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "unknown"
                    val data = mapOf(
                        "lat" to location.latitude,
                        "lon" to location.longitude,
                        "timestamp" to System.currentTimeMillis()
                    )

                    val db = FirebaseDatabase.getInstance().reference
                    db.child("ubicaciones").child(uid).setValue(data)
                }
            }
    }
}
