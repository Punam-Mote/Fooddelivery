package com.punam.foodapp

import com.punam.foodapp.api.ServiceBuilder
import com.punam.foodapp.entity.User
import com.punam.foodapp.repository.SupplierRepository
import com.punam.foodapp.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class LoginTest {

        private lateinit var userRepository:UserRepository
        //private lateinit var supplierRepository: SupplierRepository
        // -----------------------------User Testing-----------------------------
        @Test
        fun Userlogin() = runBlocking {
            userRepository = UserRepository()
            val response = userRepository.loginUser("vero@gmail.com","veronica")
            val expectedResult = true
            val actualResult = response.success
            Assert.assertEquals(expectedResult, actualResult)
        }
//        @Test
//        fun registerUser() = runBlocking {
//            val user =
//                    User(username = username, email = email, password = password, contact=contact)
//            userRepository = UserRepository()
//            val response = userRepository.registerUser(user)
//            val expectedResult = true
//            val actualResult = response.success
//            Assert.assertEquals(expectedResult, actualResult)
//        }
//        // -----------------------------Student Testing-----------------------------
//        @Test
//        fun addStudent() = runBlocking {
//            userRepository = UserRepository()
//            supplierRepository = SupplierRepository()
//            val student =
//                    Student(fullname = "fullName", age = 33, gender = "gender", address = "address")
//            ServiceBuilder.token ="Bearer " + userRepository.checkUser("kiran","kiran123").token
//            val expectedResult = true
//            val actualResult = studentRepository.insertStudent(student).success
//            Assert.assertEquals(expectedResult, actualResult)
//        }
    }
