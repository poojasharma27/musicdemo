package com.musicdemo.ui

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.musicdemo.databinding.ActivityContactBinding


class ContactActivity : AppCompatActivity() {

   private var binding:ActivityContactBinding?=null
    private lateinit var listData: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding= ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        listData = ArrayList()
        fetchContact()
    }

    private fun fetchContact() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 0)
        }
        val resolver = contentResolver
        val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection: Array<String>? = null //use to access column-wise Data
        val selection: String? = null //use to access Row-Wise Data
        val selectionArgs: Array<String>? = null
        val order: String? = null
        val cursor: Cursor? = resolver.query(uri, projection, selection, selectionArgs, order)
        if (cursor?.count!! >0) {
            while (cursor?.moveToNext() == true) {
                val name: String =
                    cursor?.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number: String =
                    cursor?.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val myContact = """
                $name
                $number
                """.trimIndent()
                listData.add(myContact)
            }
        }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData)
        binding?.list?.setAdapter(adapter)
    }
}