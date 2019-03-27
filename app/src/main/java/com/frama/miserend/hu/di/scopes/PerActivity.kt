package com.frama.miserend.hu.di.scopes

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * Created by Balazs_Maczak on 1/4/2017.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class PerActivity
