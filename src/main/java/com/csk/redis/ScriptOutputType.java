// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis;

/**
 * A Lua script returns one of the following types:
 *
 * <ul>
 *  <li>{@link #BOOLEAN} boolean</li>
 *  <li>{@link #INTEGER} 64-bit integer</li>
 *  <li>{@link #STATUS}  status string</li>
 *  <li>{@link #VALUE}   value</li>
 *  <li>{@link #MULTI}   of these types</li>.
 * </ul>
 *
 * @author Karthik C Sridhar
 */
public enum ScriptOutputType {
    BOOLEAN, INTEGER, MULTI, STATUS, VALUE
}

