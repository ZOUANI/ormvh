import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEvaluation } from 'app/shared/model/evaluation.model';
import { AccountService } from 'app/core';
import { EvaluationService } from './evaluation.service';

@Component({
  selector: 'jhi-evaluation',
  templateUrl: './evaluation.component.html'
})
export class EvaluationComponent implements OnInit, OnDestroy {
  evaluations: IEvaluation[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected evaluationService: EvaluationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.evaluationService
      .query()
      .pipe(
        filter((res: HttpResponse<IEvaluation[]>) => res.ok),
        map((res: HttpResponse<IEvaluation[]>) => res.body)
      )
      .subscribe(
        (res: IEvaluation[]) => {
          this.evaluations = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEvaluations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEvaluation) {
    return item.id;
  }

  registerChangeInEvaluations() {
    this.eventSubscriber = this.eventManager.subscribe('evaluationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
