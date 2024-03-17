package com.dk.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.dk.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val joinBtnClicked = findViewById<Button>(R.id.joinBtn)
        joinBtnClicked.setOnClickListener{
            /**
             * 1. 회원가입 방법 : 동적으로 아이디,비밀번호 파람 주입
             */
//        val email = findViewById<EditText>(R.id.emailArea)
//        val pwd = findViewById<EditText>(R.id.pwdArea)

            /**
             * 2. 회원가입 방법 : 데이터 바인딩
             * build.gradle에
             * buildFeatures{dataBinding = true}
            추가
             */
            val email = binding.emailArea
            val pwd = binding.pwdArea

            //회원가입
            auth.createUserWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"ok",Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this,"no",Toast.LENGTH_LONG).show()
                    }
                }
        }

        //로그아웃
        binding.logoutBtn.setOnClickListener{
            auth.signOut()
        }


        //로그인
        binding.loginBtn.setOnClickListener{
            val email = binding.emailArea
            val pwd = binding.pwdArea

            auth.signInWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"login is complete",Toast.LENGTH_LONG).show()
                        // uuid값 호출
                        Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, BoardListActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this,"no",Toast.LENGTH_LONG).show()
                    }
                }

        }

    }


}