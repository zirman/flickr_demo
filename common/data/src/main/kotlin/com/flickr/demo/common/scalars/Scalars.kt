package com.flickr.demo.common.scalars

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@JvmInline
value class Title(val string: String) : Parcelable

@Parcelize
@Serializable
@JvmInline
value class Description(val string: String) : Parcelable

@Parcelize
@Serializable
@JvmInline
value class Modified(val string: String) : Parcelable

@Parcelize
@Serializable
@JvmInline
value class Generator(val string: String) : Parcelable

@Parcelize
@Serializable
@JvmInline
value class Link(val string: String) : Parcelable

@Parcelize
@Serializable
@JvmInline
value class Url(val string: String) : Parcelable

@Parcelize
@Serializable
@JvmInline
value class Date(val string: String) : Parcelable

@Parcelize
@Serializable
@JvmInline
value class Author(val string: String) : Parcelable

@Parcelize
@Serializable
@JvmInline
value class AuthorId(val string: String) : Parcelable

@Parcelize
@Serializable
@JvmInline
value class Tags(val string: String) : Parcelable
