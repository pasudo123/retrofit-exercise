package com.example.retrofitexercise.client.shortnews

import com.example.retrofitexercise.shortnews.resources.ShortNewsResources
import com.fasterxml.jackson.databind.ObjectMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShortNewsDummyClient(
    private val mapper: ObjectMapper
): ShortNewsClient{

    override fun getNewsByCategory(category: String): Call<ShortNewsResources.Response> {
        val responseString = """
        {
            "category": "$category",
            "data": [
            {
                "author": "Anmol Sharma",
                "content": "Her Majesty's Revenue and Customs (HMRC), tax authority of UK government, mistakenly deposited around £750,000 (₹7.7 crore) in a woman's account. The woman came forward recently to reveal she had received the amount in August 2020 and waited for HMRC to reclaim it. The woman added she spent around £20,000 and was worried what'd happen if HMRC got to know.",
                "date": "05 Dec 2021,Sunday",
                "imageUrl": "https://static.inshorts.com/inshorts/images/v1/variants/jpg/m/2021/12_dec/5_sun/img_1638688199858_380.jpg?",
                "readMoreUrl": "https://www.timesnownews.com/amp/the-buzz/article/woman-finds-mystery-rs-7-crore-in-her-bank-account-turns-out-it-was-a-mistake-by-the-revenue-department/837940?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts ",
                "time": "12:53 pm",
                "title": "₹7.7 crore credited in UK woman's bank account by mistake",
                "url": "https://www.inshorts.com/en/news/₹77-crore-credited-in-uk-womans-bank-account-by-mistake-1638689004300"
            }],
            "success": "true"
        }         
        """.trimIndent()

        val response = mapper.readValue(responseString, ShortNewsResources.Response::class.java)

        return TODO("")
//        return Callback<ShortNewsResources.Response> {
//            @Override
//            fun onResponse(call: Call<ShortNewsResources.Response>, response: Response<ShortNewsResources.Response>) {
//
//            }
//
//            fun onFailure(call: Call<ShortNewsResources.Response>, t: Throwable) {
//
//            }
//        }
    }
}