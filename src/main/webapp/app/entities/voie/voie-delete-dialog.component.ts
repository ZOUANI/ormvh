import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVoie } from 'app/shared/model/voie.model';
import { VoieService } from './voie.service';

@Component({
  selector: 'jhi-voie-delete-dialog',
  templateUrl: './voie-delete-dialog.component.html'
})
export class VoieDeleteDialogComponent {
  voie: IVoie;

  constructor(protected voieService: VoieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.voieService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'voieListModification',
        content: 'Deleted an voie'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-voie-delete-popup',
  template: ''
})
export class VoieDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ voie }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VoieDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.voie = voie;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/voie', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/voie', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
