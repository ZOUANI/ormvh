import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEvaluation } from 'app/shared/model/evaluation.model';
import { EvaluationService } from './evaluation.service';
import { EvaluationDeleteDialogComponent } from './evaluation-delete-dialog.component';

@Component({
  selector: 'jhi-evaluation',
  templateUrl: './evaluation.component.html',
})
export class EvaluationComponent implements OnInit, OnDestroy {
  evaluations?: IEvaluation[];
  eventSubscriber?: Subscription;

  constructor(protected evaluationService: EvaluationService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.evaluationService.query().subscribe((res: HttpResponse<IEvaluation[]>) => (this.evaluations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEvaluations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEvaluation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEvaluations(): void {
    this.eventSubscriber = this.eventManager.subscribe('evaluationListModification', () => this.loadAll());
  }

  delete(evaluation: IEvaluation): void {
    const modalRef = this.modalService.open(EvaluationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.evaluation = evaluation;
  }
}
