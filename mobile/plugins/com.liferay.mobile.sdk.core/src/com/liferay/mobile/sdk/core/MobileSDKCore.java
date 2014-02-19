/*******************************************************************************
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 *******************************************************************************/

package com.liferay.mobile.sdk.core;


import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.android.v62.portal.PortalService;
import com.liferay.mobile.android.v62.portlet.PortletService;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.json.JSONArray;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 *
 * @author Gregory Amerson
 */
public class MobileSDKCore extends Plugin
{

    // The shared instance
    private static MobileSDKCore plugin;

    // The plug-in ID
    public static final String PLUGIN_ID = "com.liferay.mobile.sdk.core"; //$NON-NLS-1$

    public static IStatus createErrorStatus( String msg, Exception e )
    {
        return new Status( IStatus.ERROR, PLUGIN_ID, msg, e );
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static MobileSDKCore getDefault()
    {
        return plugin;
    }

    public static void logError( String msg, Exception e )
    {
        getDefault().getLog().log( createErrorStatus( msg, e ) );
    }

    /**
     * The constructor
     */
    public MobileSDKCore()
    {
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start( BundleContext context ) throws Exception
    {
        super.start( context );
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop( BundleContext context ) throws Exception
    {
        plugin = null;
        super.stop( context );
    }

    private static <T extends BaseService> T getService(
        String server, String username, String password, Class<T> serviceClass ) throws Exception
    {
        return serviceClass.getConstructor( Session.class ).newInstance( new SessionImpl( server, username, password ) );
    }

    public static void asdf( final String server, final String username, final String password )
    {
        try
        {
            final PortletService portletService = getService( server, username, password, PortletService.class );

            final Object warPortletsObject = portletService.getWarPortlets();

            if( warPortletsObject instanceof JSONArray )
            {
                System.out.println(( (JSONArray) warPortletsObject ).length());
            }
        }
        catch( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public static Object checkServerStatus(final String server, final String username, final String password )
    {
        Object retval = null;

        try
        {
            final PortalService portalService = getService( server, username, password, PortalService.class );
            retval = portalService.getBuildNumber();
        }
        catch( Exception e )
        {
            retval = e.getMessage();
        }

        return retval;
    }
}
