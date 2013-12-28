/*
 * Created on 12-10-15
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2012 the original author or authors.
 */
package chapter02_utils;

import java.lang.ref.*;

/**
 * Soft reference / Weak reference / Phantom reference
 *
 * @author XiongNeng
 * @version 1.0
 * @since 12-10-15
 */
public class References {
    public static void ref() {
        Object weakObj, phantomObj;
        Reference ref;
        WeakReference weakRef;
        PhantomReference phantomRef;
        ReferenceQueue weakQueue, phantomQueue;

        weakObj = new String("Weak Reference");
        phantomObj = new String("Phantom Reference");
        weakQueue = new ReferenceQueue();
        phantomQueue = new ReferenceQueue();
        weakRef = new WeakReference(weakObj, weakQueue);
        phantomRef = new PhantomReference(phantomObj, phantomQueue);
        // Clear all strong references
        weakObj = null;
        phantomObj = null;

        // Print referents to prove they exist.  Phantom referents
        // are inaccessible so we should see a null value.
        System.out.println("Before gc(), Weak Reference: " + weakRef.get());
        System.out.println("Before gc(), Phantom Reference: " + phantomRef.get());

        // Invoke garbage collector in hopes that references
        // will be queued
        System.gc();

        // Print referents to prove they exist.  Phantom referents
        // are inaccessible so we should see a null value.
        System.out.println("After gc(), Weak Reference: " + weakRef.get());
        System.out.println("After gc(), Phantom Reference: " + phantomRef.get());

        // See if the garbage collector has queued the references
        System.out.println("Weak Queued: " + weakRef.isEnqueued());
        // Try to finalize the phantom references if not already
        if (!phantomRef.isEnqueued()) {
            System.out.println("Requestion finalization.");
            System.runFinalization();
        }
        System.out.println("Phantom Queued: " + phantomRef.isEnqueued());

        // Wait until the weak reference is on the queue and remove it
        try {
            ref = weakQueue.remove();
            // The referent should be null
            System.out.println("After weakQueue remove(), Weak Reference: " + ref.get());
            // Wait until the phantom reference is on the queue and remove it
            ref = phantomQueue.remove();
            System.out.println("After phantomQueue remove(), Phantom Reference: " + ref.get());
            // We have to clear the phantom referent even though
            // get() returns null
            ref.clear();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}