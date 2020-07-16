import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { BordereauDetailComponent } from 'app/entities/bordereau/bordereau-detail.component';
import { Bordereau } from 'app/shared/model/bordereau.model';

describe('Component Tests', () => {
  describe('Bordereau Management Detail Component', () => {
    let comp: BordereauDetailComponent;
    let fixture: ComponentFixture<BordereauDetailComponent>;
    const route = ({ data: of({ bordereau: new Bordereau(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [BordereauDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BordereauDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BordereauDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bordereau on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bordereau).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
