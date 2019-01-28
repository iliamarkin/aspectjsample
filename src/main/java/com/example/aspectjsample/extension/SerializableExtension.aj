package com.example.aspectjsample.extension;

public aspect SerializableExtension {

    declare parents : com.example.aspectjsample.bo..* implements java.io.Serializable;
}
