package com.mobile.android.feature.searchrepository.model

import com.google.gson.annotations.SerializedName

/**
 * 	Used as model for Network Call
 */
data class RepoResponse(

	@field:SerializedName("total_count")
	val totalCount: Int? = null,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean? = null,

	@field:SerializedName("items")
	val items: List<ItemsItem?>? = null
)

data class ItemsItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stargazers_count")
	val stargazersCount: Int? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("html_url")
	val htmlUrl: String? = null,

	@field:SerializedName("owner")
	val owner: Owner? = null,
)

data class Owner(

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("url")
	val url: String? = null,
)
