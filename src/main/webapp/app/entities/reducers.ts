import school from 'app/entities/school/school.reducer';
import graduation from 'app/entities/graduation/graduation.reducer';
import grad from 'app/entities/grad/grad.reducer';
import jobHistory from 'app/entities/job-history/job-history.reducer';
import skill from 'app/entities/skill/skill.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  school,
  graduation,
  grad,
  jobHistory,
  skill,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
