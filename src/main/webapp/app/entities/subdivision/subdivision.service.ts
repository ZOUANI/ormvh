import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubdivision } from 'app/shared/model/subdivision.model';

type EntityResponseType = HttpResponse<ISubdivision>;
type EntityArrayResponseType = HttpResponse<ISubdivision[]>;

@Injectable({ providedIn: 'root' })
export class SubdivisionService {
  public resourceUrl = SERVER_API_URL + 'api/subdivisions';

  constructor(protected http: HttpClient) {}

  create(subdivision: ISubdivision): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subdivision);
    return this.http
      .post<ISubdivision>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(subdivision: ISubdivision): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subdivision);
    return this.http
      .put<ISubdivision>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISubdivision>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubdivision[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(subdivision: ISubdivision): ISubdivision {
    const copy: ISubdivision = Object.assign({}, subdivision, {
      createdAt: subdivision.createdAt && subdivision.createdAt.isValid() ? subdivision.createdAt.toJSON() : undefined,
      updatedAt: subdivision.updatedAt && subdivision.updatedAt.isValid() ? subdivision.updatedAt.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((subdivision: ISubdivision) => {
        subdivision.createdAt = subdivision.createdAt ? moment(subdivision.createdAt) : undefined;
        subdivision.updatedAt = subdivision.updatedAt ? moment(subdivision.updatedAt) : undefined;
      });
    }
    return res;
  }
}
