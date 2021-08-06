package com.musicdemo.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.musicdemo.Music
import com.musicdemo.databinding.ActivityContactBinding


class ContactActivity : AppCompatActivity() {

   private var binding:ActivityContactBinding?=null
    private var listData: ArrayList<String> = ArrayList()
  private   val projection: Array<String>? = null //use to access column-wise Data
  private  val selection: String? = null //use to access Row-Wise Data
  private  val selectionArgs: Array<String>? = null
  private  val order: String? = null
    var name: String?=null
    var number: String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding= ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        fetchContact()
        setUpList()
    }

    private fun setUpList() {
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData)
        binding?.list?.setAdapter(adapter)
       binding?.list?.setOnItemClickListener(object:OnItemClickListener{
           override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               val value: String = binding?.list?.getAdapter()?.getItem(p2).toString()

               sendContact(value)
           }

       })
    }

    private fun sendContact(position:String) {
        val intent = Intent(Music.ContactNumber.name)
        intent.putExtra("Name", position)
        Log.e("Name",position.toString())
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun fetchContact() {
        requestPermission()
        val resolver = contentResolver
        val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor: Cursor? = resolver.query(uri, projection, selection, selectionArgs, order)
        if (cursor?.count!! >0) {
            while (cursor?.moveToNext()) {
               name = cursor?.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                 number = cursor?.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val myContact = """$name$number """.trimIndent()
                listData.add(myContact)
            }
        }

    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 0)
        }
    }

}