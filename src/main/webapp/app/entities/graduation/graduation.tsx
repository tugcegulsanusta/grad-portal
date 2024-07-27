import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './graduation.reducer';

export const Graduation = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const graduationList = useAppSelector(state => state.graduation.entities);
  const loading = useAppSelector(state => state.graduation.loading);
  const totalItems = useAppSelector(state => state.graduation.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="graduation-heading" data-cy="GraduationHeading">
        Graduations
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Listeyi yenile
          </Button>
          <Link to="/graduation/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Yeni Graduation ekle
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {graduationList && graduationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('startYear')}>
                  Start Year <FontAwesomeIcon icon={getSortIconByFieldName('startYear')} />
                </th>
                <th className="hand" onClick={sort('graduationYear')}>
                  Graduation Year <FontAwesomeIcon icon={getSortIconByFieldName('graduationYear')} />
                </th>
                <th className="hand" onClick={sort('gpa')}>
                  Gpa <FontAwesomeIcon icon={getSortIconByFieldName('gpa')} />
                </th>
                <th>
                  School Id <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Grad Id <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {graduationList.map((graduation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/graduation/${graduation.id}`} color="link" size="sm">
                      {graduation.id}
                    </Button>
                  </td>
                  <td>{graduation.startYear}</td>
                  <td>{graduation.graduationYear}</td>
                  <td>{graduation.gpa}</td>
                  <td>{graduation.schoolId ? <Link to={`/school/${graduation.schoolId.id}`}>{graduation.schoolId.id}</Link> : ''}</td>
                  <td>{graduation.gradId ? <Link to={`/grad/${graduation.gradId.id}`}>{graduation.gradId.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/graduation/${graduation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Görüntüle</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/graduation/${graduation.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Düzenle</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/graduation/${graduation.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`)
                        }
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Sil</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">Graduations kaydi bulunamadi</div>
        )}
      </div>
      {totalItems ? (
        <div className={graduationList && graduationList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Graduation;
