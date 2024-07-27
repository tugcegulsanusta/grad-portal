import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Graduation from './graduation';
import GraduationDetail from './graduation-detail';
import GraduationUpdate from './graduation-update';
import GraduationDeleteDialog from './graduation-delete-dialog';

const GraduationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Graduation />} />
    <Route path="new" element={<GraduationUpdate />} />
    <Route path=":id">
      <Route index element={<GraduationDetail />} />
      <Route path="edit" element={<GraduationUpdate />} />
      <Route path="delete" element={<GraduationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GraduationRoutes;
