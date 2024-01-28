import {create} from 'zustand'

export const useUsernameStore = create((set) => {

    return {
        username: 'Login',
        setUsername: (newUsername) => set({username: newUsername}),
    };
});
