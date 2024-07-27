import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import School from './school';
import Graduation from './graduation';
import Grad from './grad';
import JobHistory from './job-history';
import Skill from './skill';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="school/*" element={<School />} />
        <Route path="graduation/*" element={<Graduation />} />
        <Route path="grad/*" element={<Grad />} />
        <Route path="job-history/*" element={<JobHistory />} />
        <Route path="skill/*" element={<Skill />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
