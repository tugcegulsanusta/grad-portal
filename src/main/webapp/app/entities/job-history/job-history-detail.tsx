import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './job-history.reducer';

export const JobHistoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const jobHistoryEntity = useAppSelector(state => state.jobHistory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jobHistoryDetailsHeading">Job History</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{jobHistoryEntity.id}</dd>
          <dt>
            <span id="companyName">Company Name</span>
          </dt>
          <dd>{jobHistoryEntity.companyName}</dd>
          <dt>
            <span id="jobTitle">Job Title</span>
          </dt>
          <dd>{jobHistoryEntity.jobTitle}</dd>
          <dt>
            <span id="startDate">Start Date</span>
          </dt>
          <dd>
            {jobHistoryEntity.startDate ? <TextFormat value={jobHistoryEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="endDate">End Date</span>
          </dt>
          <dd>{jobHistoryEntity.endDate ? <TextFormat value={jobHistoryEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="isCurrent">Is Current</span>
          </dt>
          <dd>{jobHistoryEntity.isCurrent ? 'true' : 'false'}</dd>
          <dt>Grad Id</dt>
          <dd>{jobHistoryEntity.gradId ? jobHistoryEntity.gradId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/job-history" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Geri</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/job-history/${jobHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Düzenle</span>
        </Button>
      </Col>
    </Row>
  );
};

export default JobHistoryDetail;
