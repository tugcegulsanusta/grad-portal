import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Grad from './grad';
import GradDetail from './grad-detail';
import GradUpdate from './grad-update';
import GradDeleteDialog from './grad-delete-dialog';

const GradRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Grad />} />
    <Route path="new" element={<GradUpdate />} />
    <Route path=":id">
      <Route index element={<GradDetail />} />
      <Route path="edit" element={<GradUpdate />} />
      <Route path="delete" element={<GradDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GradRoutes;
