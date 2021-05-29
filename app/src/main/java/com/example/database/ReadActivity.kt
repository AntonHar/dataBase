package com.example.database

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.util.*


class ReadActivity : AppCompatActivity() {
    private lateinit var listView:ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var listData: MutableList<String>
    private lateinit var listTemp: MutableList<User>
    private lateinit var mDataBase: DatabaseReference
    private val USER_KEY = "user"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.read_layout)
        init()
        dataFromDB
        setOnClickItem()
    }

    private fun init() {
        listView = findViewById(R.id.listView)
        listData = ArrayList()
        listTemp = ArrayList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
            listData as ArrayList<String>
        )
        listView.adapter = adapter
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY)
    }

    private val dataFromDB: Unit
        private get() {
            val vListener: ValueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (listData!!.size > 0) listData!!.clear()
                    if (listTemp!!.size > 0) listTemp!!.clear()
                    for (ds in dataSnapshot.children) {
                        val user = ds.getValue(
                            User::class.java
                        )!!
                        user.name?.let { listData!!.add(it) }
                        listTemp!!.add(user)
                    }
                    adapter!!.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            }
            mDataBase!!.addValueEventListener(vListener)
        }

    private fun setOnClickItem() {
        listView!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val user = listTemp!![position]
                val i = Intent(this@ReadActivity, ShowActivity::class.java)
                i.putExtra(Constant.USER_NAME, user!!.name)
                i.putExtra(Constant.USER_SEC_NAME, user.sec_name)
                i.putExtra(Constant.USER_EMAIL, user.email)
                startActivity(i)
            }
    }
}






