package com.example.github.data.remote.api

import com.example.github.data.ObserverTestUtils.getJson
import com.example.github.data.model.RepoEntity
import com.example.github.data.model.UserEntity
import io.reactivex.observers.TestObserver
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class GithubApiTest {

    private lateinit var githubApi: GithubApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        githubApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun testMockResponse() {
        val mockedResponse = MockResponse()

        mockedResponse.setResponseCode(200)
        mockedResponse.setBody("{}")

        mockWebServer.enqueue(mockedResponse)
    }

    @Test
    fun testGetUserOk() {
        val testObserver = TestObserver<UserEntity>()
        val id = "google"
        val path = "/users/$id"

        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("user.json"))

        // Enqueue request
        mockWebServer.enqueue(mockResponse)

        // Call API
        githubApi.getUser(id).toObservable().subscribe(testObserver)
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // then no error
        testObserver.assertNoErrors()

        // check values
        testObserver.assertValueCount(1)

        // get request to test
        val request = mockWebServer.takeRequest()
        assertEquals(request.path, path)

        // test success value by search.json
        testObserver.assertValue { userResponse ->
            userResponse.name.equals("Google") && userResponse.id == 1342004
        }
    }

    @Test
    fun testGetUserError() {
        val testObserver = TestObserver<UserEntity>()
        val id = "google"

        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(500)

        // Enqueue request
        mockWebServer.enqueue(mockResponse)

        // Call API
        githubApi.getUser(id).toObservable().subscribe(testObserver)
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // no value
        testObserver.assertNoValues()
    }

    @Test
    fun testGetReposOk() {
        val testObserver = TestObserver<List<RepoEntity>>()
        val id = "google"
        val page = 1

        val path = "/users/$id/repos?page=$page&per_page=25"

        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(getJson("repos.json"))

        // Enqueue request
        mockWebServer.enqueue(mockResponse)

        // Call API
        githubApi.getRepos(id, page, 25).toObservable().subscribe(testObserver)
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // then no error
        testObserver.assertNoErrors()

        // check values
        testObserver.assertValueCount(1)

        // get request to test
        val request = mockWebServer.takeRequest()
        assertEquals(request.path, path)

        // test success value by search.json
        testObserver.assertValue { repos ->
            repos.size == 25 && repos[0].id == 170908616
        }
    }

    @Test
    fun testGetReposError() {
        val testObserver = TestObserver<List<RepoEntity>>()
        val id = "google"
        val page = 10

        // Mock a response with status 200 and sample JSON output
        val mockResponse = MockResponse()
            .setResponseCode(500)

        // Enqueue request
        mockWebServer.enqueue(mockResponse)

        // Call API
        githubApi.getRepos(id, page).toObservable().subscribe(testObserver)
        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        // no value
        testObserver.assertNoValues()
    }
}
