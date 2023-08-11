import { createSlice } from '@reduxjs/toolkit';
import { FolderType } from '../interfaces/data';

const initialState: FolderType = { name: 'Root', itemId: 0, parentId: null };

export const currentFolderSlice = createSlice({
	name: 'currentFolder',
	initialState,
	reducers: {
		select: (state, { payload }) => {
			state.itemId = payload.itemId;
			state.name = payload.name;
			state.parentId = payload.parentId;
		},
	},
});

export const { select } = currentFolderSlice.actions;
export default currentFolderSlice.reducer;
