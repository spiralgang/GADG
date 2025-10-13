//
// The contents of this file are subject to the Mozilla Public
// License Version 1.1 (the "License"); you may not use this file
// except in compliance with the License. You may obtain a copy
// of the License at http://www.mozilla.org/MPL/
//
// Software distributed under the License is distributed on an
// "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
// implied. See the License for the specific language governing
// rights and limitations under the License.
//
// The Original Code is  State Machine Compiler(SMC).
//
// The Initial Developer of the Original Code is Charles W. Rapp.
// Portions created by Charles W. Rapp are
// Copyright (C) 2000 - 2009,  2021. Charles W. Rapp.
// All Rights Reserved.
//
// Contributor(s):
//
// statemap.java --
//
//  This package defines the FSMContext class which must be
//  inherited by any Java class wanting to use an smc-generated
//  state machine.
//
// RCS ID
// Id: FSMContext.java,v 1.16 2013/07/14 14:32:36 cwrapp Exp
//
// CHANGE LOG
// (See the bottom of this file.)
//

package statemap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * Base class for all SMC-generated application context classes.
 * This class stores the FSM name, current and previous states,
 * the state stack, debugging information and state change
 * listeners.
 *
 * @author <a href="mailto:rapp@acm.org">Charles Rapp</a>
 */

public abstract class FSMContext
    extends AbstractFSMContext
    implements Serializable
{
//---------------------------------------------------------------
// Member data.
//

    //-----------------------------------------------------------
    // Constants.
    //

    private static final long serialVersionUID = 0x070300L;

    //-----------------------------------------------------------
    // Locals.
    //

    /**
     * The FSM name.
     */
    protected transient String mName;

    /**
     * The current state. Will be {@code null} while in
     * transition.
     */
    protected transient State mState;

    /**
     * Stores which state a transition left. May be {@code null}.
     */
    protected transient State mPreviousState;

    /**
     * This stack is used to store the current state when a push
     * transition is taken.
     */
    protected transient List<State> mStateStack;

//---------------------------------------------------------------
// Member functions.
//

    //-----------------------------------------------------------
    // Constructors.
    //

    /**
     * Creates a finite state machine context for the given
     * initial state.
     * @param initState the finite state machine's start state.
     */
    protected FSMContext(State initState)
    {
        mState = initState;
        mPreviousState = null;
        mStateStack = null;
    } // end of FSMContext(State)

    //
    // end of Constructors.
    //-----------------------------------------------------------

    //-----------------------------------------------------------
    // Abstract Method Implementations.
    //

    /**
     * Returns previous state name. If there is no previous
     * state then returns an empty string.
     * @return previous state name or an empty string.
     */
    @Override
    protected String previousStateName()
    {
        return (mPreviousState == null ?
                NOT_SET :
                mPreviousState.getName());
    } // end of previousStateName()

    //
    // end of Abstract Method Implementatoins.
    //-----------------------------------------------------------

    //-----------------------------------------------------------
    // Abstract method declarations.
    //

    /**
     * Starts the finite state machine running by executing the
     * initial state's entry actions.
     */
    public abstract void enterStartState();

    //
    // end of Abstract method declarations.
    //-----------------------------------------------------------

    //-----------------------------------------------------------
    // Serializable Interface Implementation.
    //

    private void readObject(ObjectInputStream istream)
        throws IOException,
               ClassNotFoundException
    {
        istream.defaultReadObject();
    } // end of readObject(ObjectInputStream)

    //
    // end of Serializable Interface Implementation.
    //-----------------------------------------------------------

    //-----------------------------------------------------------
    // Get methods.
    //

    /**
     * Returns {@code true} if this FSM is in a transition and
     * {@code false} otherwise.
     * @return {@code true} if this FSM is in a transition and
     * {@code false} otherwise.
     */
    public boolean isInTransition()
    {
        return (mState == null);
    } // end of isInTransition()

    // NOTE: getState() is defined in the SMC-generated
    // FSMContext subclass.

    /**
     * If this FSM is in transition, then returns the previous
     * state which the last transition left.
     * @return the previous state which the current transition
     * left. May return {@code null}.
     */
    public State getPreviousState()
    {
        return (mPreviousState);
    } // end of getPreviousState()

    //
    // end of Get methods.
    //-----------------------------------------------------------

    //-----------------------------------------------------------
    // Set methods.
    //

    /**
     * Sets the current state to the given value.
     * @param state The current state.
     */
    public void setState(final State state)
    {
        if (getDebugFlag())
        {
            getDebugStream().println("ENTER STATE     : " +
                                     state.getName());
        }

        // clearState() is not called when a transition has
        // no actions, so set mPreviousState to mState in
        // that situation. We know clearState() was not
        // called when mState is not null.
        if (mState != null)
        {
            mPreviousState = mState;
        }

        mState = state;

        // Inform any and all listeners about this state change.
        propertyChange(mPreviousState, mState);
    } // end of setState(State)

    /**
     * Places the current state into the previous state sets
     * the current state to {@code null}.
     */
    public void clearState()
    {
        mPreviousState = mState;
        mState = null;
    } // end of clearState()

    /**
     * Pushes the current state on top of the state stack and
     * sets the current state to {@code state}.
     * @param state The new current state.
     * @exception NullPointerException
     * if {@code state} is {@code null}.
     */
    public void pushState(State state)
    {
        if (mState == null)
        {
            throw (new NullPointerException());
        }

        if (getDebugFlag())
        {
            getDebugStream().println("PUSH TO STATE   : " +
                                     state.getName());
        }

        if (mStateStack == null)
        {
            mStateStack = new ArrayList<>();
        }

        mPreviousState = mState;
        mStateStack.add(0, mState);
        mState = state;

        // Inform any and all listeners about this state change.
        propertyChange(mPreviousState, mState);
    } // end of pushState(State)

    /**
     * Sets the previous state to the current state and pops
     * the top state off the stack and places it into the
     * current state.
     * @exception EmptyStackException
     * if the state stack is empty.
     */
    public void popState()
    {
        if (mStateStack == null || mStateStack.isEmpty())
        {
            if (getDebugFlag())
            {
                getDebugStream().println(
                    "POPPING ON EMPTY STATE STACK.");
            }

            throw (new EmptyStackException());
        }
        else
        {
            // clearState() is not called when a transition has
            // no actions, so set mPreviousState to mState in
            // that situation. We know clearState() was not
            // called when mState is not null.
            if (mState != null)
            {
                mPreviousState = mState;
            }

            // The pop method removes the top element
            // from the stack and returns it.
            // Do not drop the state stack. If it was used once
            // then it will likely be used again.
            mState = mStateStack.get(0);

            if (getDebugFlag())
            {
                getDebugStream().println("POP TO STATE    : " +
                                         mState.getName());
            }

            // Inform any and all listeners about this state
            // change.
            propertyChange(mPreviousState, mState);
        }
    } // end of popState()

    /**
     * Empties the state stack.
     */
    public void emptyStateStack()
    {
        if (mStateStack != null)
        {
            mStateStack.clear();
            mStateStack = null;
        }
    } // end of emptyStateStack()

    //
    // end of Set methods.
    //-----------------------------------------------------------
} // end of class FSMContext

//
// CHANGE LOG
// Log: FSMContext.java,v
// Revision 1.16  2013/07/14 14:32:36  cwrapp
// check in for release 6.2.0
//
// Revision 1.15  2011/11/20 14:58:33  cwrapp
// Check in for SMC v. 6.1.0
//
// Revision 1.14  2009/11/24 20:42:39  cwrapp
// v. 6.0.1 update
//
// Revision 1.13  2009/09/05 15:39:20  cwrapp
// Checking in fixes for 1944542, 1983929, 2731415, 2803547 and feature 2797126.
//
// Revision 1.12  2009/03/27 09:41:07  cwrapp
// Added F. Perrad changes back in.
//
// Revision 1.11  2009/03/01 18:20:40  cwrapp
// Preliminary v. 6.0.0 commit.
//
// Revision 1.10  2008/01/14 19:59:23  cwrapp
// Release 5.0.2 check-in.
//
// Revision 1.9  2007/08/05 13:00:34  cwrapp
// Version 5.0.1 check-in. See net/sf/smc/CODE_README.txt for more information.
//
// Revision 1.8  2007/02/21 13:50:59  cwrapp
// Moved Java code to release 1.5.0
//
// Revision 1.7  2005/05/28 18:44:13  cwrapp
// Updated C++, Java and Tcl libraries, added CSharp, Python
// and VB.
//
// Revision 1.1  2005/02/21 19:03:38  charlesr
// Variable name clean up.
//
// Revision 1.0  2003/12/14 20:38:40  charlesr
// Initial revision
//
