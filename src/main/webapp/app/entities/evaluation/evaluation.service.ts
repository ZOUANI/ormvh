import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEvaluation } from 'app/shared/model/evaluation.model';

type EntityResponseType = HttpResponse<IEvaluation>;
type EntityArrayResponseType = HttpResponse<IEvaluation[]>;

@Injectable({ providedIn: 'root' })
export class EvaluationService {
  public resourceUrl = SERVER_API_URL + 'api/evaluations';

  constructor(protected http: HttpClient) {}

  create(evaluation: IEvaluation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(evaluation);
    return this.http
      .post<IEvaluation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(evaluation: IEvaluation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(evaluation);
    return this.http
      .put<IEvaluation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEvaluation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEvaluation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(evaluation: IEvaluation): IEvaluation {
    const copy: IEvaluation = Object.assign({}, evaluation, {
      createdAt: evaluation.createdAt != null && evaluation.createdAt.isValid() ? evaluation.createdAt.toJSON() : null,
      updatedAt: evaluation.updatedAt != null && evaluation.updatedAt.isValid() ? evaluation.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((evaluation: IEvaluation) => {
        evaluation.createdAt = evaluation.createdAt != null ? moment(evaluation.createdAt) : null;
        evaluation.updatedAt = evaluation.updatedAt != null ? moment(evaluation.updatedAt) : null;
      });
    }
    return res;
  }
}
