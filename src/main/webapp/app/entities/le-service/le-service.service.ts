import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILeService } from 'app/shared/model/le-service.model';

type EntityResponseType = HttpResponse<ILeService>;
type EntityArrayResponseType = HttpResponse<ILeService[]>;

@Injectable({ providedIn: 'root' })
export class LeServiceService {
  public resourceUrl = SERVER_API_URL + 'api/le-services';

  constructor(protected http: HttpClient) {}

  create(leService: ILeService): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leService);
    return this.http
      .post<ILeService>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(leService: ILeService): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leService);
    return this.http
      .put<ILeService>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILeService>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILeService[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(leService: ILeService): ILeService {
    const copy: ILeService = Object.assign({}, leService, {
      createdAt: leService.createdAt && leService.createdAt.isValid() ? leService.createdAt.toJSON() : undefined,
      updatedAt: leService.updatedAt && leService.updatedAt.isValid() ? leService.updatedAt.toJSON() : undefined,
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
      res.body.forEach((leService: ILeService) => {
        leService.createdAt = leService.createdAt ? moment(leService.createdAt) : undefined;
        leService.updatedAt = leService.updatedAt ? moment(leService.updatedAt) : undefined;
      });
    }
    return res;
  }
}
